import { Injectable } from '@angular/core';
import { BuchungDto } from '../facade/dto/BuchungDto';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { Kennzeichen } from '../facade/Kennzeichen';
import { AccountService } from './account.service';
import { ServiceBase } from './service-utils';
import { BuchungUebersichtDto } from '../facade/dto/BuchungUebersichtDto';

@Injectable({
  providedIn: 'root'
})
export class BuchungsuebersichtService extends ServiceBase {

  constructor(private http: HttpClient, private accountService: AccountService) {
    super();
  }
  
  public getAllBuchungen(): Observable<BuchungUebersichtDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<BuchungUebersichtDto[]>(`${environment.apiServerUrl}/buchungen/all`)
    );
  }

  public getAllBuchungenMappedByDatum(): Observable<any> {
    return this.wrapRetryAndCatchError(
      this.http.get<BuchungUebersichtDto[]>(`${environment.apiServerUrl}/buchungen/all/datum`)
    );
  }

  public getAllBuchungenMappedByMitarbeiter(): Observable<any> {
    return this.wrapRetryAndCatchError(
      this.http.get<BuchungUebersichtDto[]>(`${environment.apiServerUrl}/buchungen/all/mitarbeiter`)
    );
  }

  public getBuchungenForMitarbeiter(mitarbeiterID: number): Observable<BuchungDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}`)
    );
  }

  public deleteBuchungFromMitarbeiter(mitarbeiterID: number, buchungID: number): Observable<BuchungDto[]> {
    return this.wrapRetryAndCatchError(
      this.http.delete<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}/buchung/${buchungID}`)
    );
  }

  public getKennzeichenForMitarbeiter(mitarbeiterID: number): Observable<Kennzeichen[]> {
    return this.wrapRetryAndCatchError(
      this.http.get<Kennzeichen[]>(`${environment.apiServerUrl}/buchungen/${mitarbeiterID}/kennzeichen`)
    );
  }

  public saveKennzeichenForBuchung(buchungID: number, kennzeichenID: number) {
    return this.wrapRetryAndCatchError(
      this.http.post<BuchungDto[]>(`${environment.apiServerUrl}/buchungen/${buchungID}/kennzeichen/${kennzeichenID}`, null)
    );
  }

}
