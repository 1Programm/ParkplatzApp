import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, retry, throwError } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../facade/dto/parkflaeche-auswahl.dto';
import { environment } from 'src/environments/environment';
import { Parkhaus } from '../facade/Parkhaus';
import { Parkflaeche } from '../facade/Parkflaeche';
import { Parkplatz } from '../facade/Parkplatz';
import { Parkplatztyp } from '../facade/Parkplatztyp';
import { Preiskategorie } from '../facade/Preiskategorie';
import { ParkplatzMitStatusDto } from '../facade/dto/ParkplatzMitStatusDto';
import { ServiceBase } from './service-utils';
import { BuchungDto } from '../facade/dto/BuchungDto';
import { Kennzeichen } from '../facade/Kennzeichen';
import { BuchungAbschlussDto } from '../facade/dto/BuchungAbschluss.dto';
import { ParkhausAdressDto } from '../facade/dto/ParkhausAdress.dto';

@Injectable({
  providedIn: 'root'
})
export class BuchungService extends ServiceBase{

  constructor(private http: HttpClient){
    super();
  }

  public getParkhausAddress(parkhausID: number): Observable<ParkhausAdressDto> {
    return this.wrapRetryAndCatchError(
      this.http.get<ParkhausAdressDto>(`${environment.apiServerUrl}/buchung/parkhaus/${parkhausID}/adresse`)
    );
  }

  public getParkflaechen(): Observable<ParkflaecheAuswahlDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<ParkflaecheAuswahlDto[]>(`${environment.apiServerUrl}/buchung/parkflaechen`)
    );
  }

  public getParkplaetzeOfParkflaeche(parkflaecheID:number): Observable<Parkplatz[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<Parkplatz[]>(`${environment.apiServerUrl}/buchung/parkplatz/${parkflaecheID}`)
    );
  }

  public getParkplaetzeOfParkflaecheAndDate(parkflaecheID:number, date: Date): Observable<ParkplatzMitStatusDto[]> {
    let datumString = date.toLocaleDateString();
    return this.wrapRetryAndCatchError(
      this.http.get<ParkplatzMitStatusDto[]>(`${environment.apiServerUrl}/buchung/parkplatz/${parkflaecheID}/${datumString}`)
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


  public getBuchungenForMitarbeiter(mitarbeiterID: number): Observable<BuchungDto[]> {
      return this.wrapRetryAndCatchError(
        this.http.get<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}`)
        );
    }

  public getKennzeichenForMitarbeiter(mitarbeiterID: number): Observable<Kennzeichen[]> {
    return this.wrapRetryAndCatchError(this.http.get<Kennzeichen[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}/kennzeichen`)
    );
  }

  public updateBuchungen(buchung: BuchungDto): Observable<BuchungDto> {
    return this.wrapRetryAndCatchError(this.http.post<BuchungDto>(`${environment.apiServerUrl}/buchung/test`, buchung)
    );
  }

  public saveBuchungen(buchungen: BuchungAbschlussDto[]){
    return this.wrapRetryAndCatchError(
      this.http.post<void>(`${environment.apiServerUrl}/buchung/abschliessen`, buchungen)
    );
  }

}
