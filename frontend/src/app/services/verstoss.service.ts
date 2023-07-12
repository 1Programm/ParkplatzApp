import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { VerstossDto } from '../facade/dto/verstoss.dto';
import { ServiceBase } from '../services/service-utils';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class VerstossService extends ServiceBase {

  constructor(private http: HttpClient) { 
    super();
  }

  public speichernVerstoss(verstossDto: VerstossDto) : Observable<VerstossDto> {
    return this.wrapRetryAndCatchError(this.http.post<VerstossDto>(`${environment.apiServerUrl}/verstoss/update`, verstossDto)
    );
  }

  public getVertoesseForMitarbeiter(mitarbeiterID: number) : Observable<VerstossDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<VerstossDto[]>(`${environment.apiServerUrl}/verstoss/verstoesse/${mitarbeiterID}`)
    );
  }

  public getAllVerstoesse() : Observable<VerstossDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<VerstossDto[]>(`${environment.apiServerUrl}/verstoss/verstoesse`)
    );
  }

  public changeStatusForVerstoss(verstossDto: VerstossDto) : Observable<VerstossDto> {
    return this.wrapRetryAndCatchError(this.http.post<VerstossDto>(`${environment.apiServerUrl}/verstoss/updateStatus`, verstossDto)
    );
  }
}
