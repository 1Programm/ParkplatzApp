import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../facade/dto/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment';
import { Parkplatz } from '../facade/Parkplatz';
import { Parkplatztyp } from '../facade/Parkplatztyp';
import { Preiskategorie } from '../facade/Preiskategorie';
import { ServiceBase } from './service-utils';

@Injectable({
  providedIn: 'root'
})
export class BuchungService extends ServiceBase{

  constructor(private http: HttpClient){
    super();
  }

  public getParkanlagen(): Observable<ParkflaecheAuswahlDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<ParkflaecheAuswahlDto[]>(`${environment.apiServerUrl}/buchung/parkanlagen`)
    );
  }

  public getParkplaetzeOfParkflaeche(parkflaecheID:number): Observable<Parkplatz[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz/${parkflaecheID}`)
    );
  }

  public getParkplatztypen(): Observable<Parkplatztyp[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<Parkplatztyp[]>(`${environment.apiServerUrl}/buchung/parkplatztypen`)
    );
  }

  public getPreiskategorien(): Observable<Preiskategorie[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<Preiskategorie[]>(`${environment.apiServerUrl}/buchung/preiskategorien`)    
    );
  }

  public saveParkplatz(parkplatz: Parkplatz): Observable<Parkplatz[]> {
    return this.wrapRetryAndCatchError(
      this.http.post<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz`, parkplatz)
    );
  }

}
