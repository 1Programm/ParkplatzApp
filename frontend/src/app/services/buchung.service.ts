import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../dtos/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment.prod';

@Injectable({
  providedIn: 'root'
})
export class BuchungService {

  constructor(private http: HttpClient){}

  public getParkanlagen(): Observable<ParkflaecheAuswahlDto[]> {
    return this.http.get<ParkflaecheAuswahlDto[]>(`${environment.backendUrl}/data/parkanlagen`);
  }
}
