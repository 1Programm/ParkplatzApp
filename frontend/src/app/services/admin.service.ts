import { Injectable } from '@angular/core';
import { ServiceBase } from './service-utils';
import { Observable } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../facade/dto/parkflaeche-auswahl.dto';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ParkhausParkflaecheDto } from '../facade/dto/ParkhausParkflaecheDto';

@Injectable({
  providedIn: 'root'
})
export class AdminService extends ServiceBase {

  constructor(private http: HttpClient) {super(); }

  
  public getAllParkhausAndParkflaeche(): Observable<ParkhausParkflaecheDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<ParkhausParkflaecheDto[]>(`${environment.apiServerUrl}/admin/parkhaus`)
    );
  }
}
