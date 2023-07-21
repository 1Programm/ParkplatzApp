import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { VerstossDto } from '../facade/dto/Verstoss.dto';
import { ServiceBase } from '../services/service-utils';
import { Observable } from 'rxjs';
import { VerstossStatusDto } from '../facade/dto/VerstossStatus.dto';

@Injectable({
  providedIn: 'root'
})
export class VerstossService extends ServiceBase {

  constructor(private http: HttpClient) { 
    super();
  }

  public speichernVerstoss(mitarbeiterID: number, verstossDto: VerstossDto) : Observable<VerstossDto> {
    return this.wrapRetryAndCatchError(this.http.post<VerstossDto>(`${environment.apiServerUrl}/verstoss/update/${mitarbeiterID}`, verstossDto)
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
  

  public getAllVerstossStatus() : Observable<VerstossStatusDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<VerstossStatusDto[]>(`${environment.apiServerUrl}/verstoss/status`)
    );
  }

  public changeStatusForVerstoss(verstossDto: VerstossDto) : Observable<VerstossDto> {
    return this.wrapRetryAndCatchError(this.http.post<VerstossDto>(`${environment.apiServerUrl}/verstoss/updateStatus`, verstossDto)
    );
  }
}
