import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _loggedIn: boolean = false;

  constructor(private http: HttpClient) { }

  public setup(){
    this.http.get<any>('/oauth2/userinfo').subscribe(info => {
      console.log("#####-", info);
      this.http.get<any>(`${environment.apiServerUrl}/mitarbeiter/id-by-technicalkey/${info.user}`).subscribe(acc => {
        console.log("++++", acc);
        this._loggedIn = true;
      });
    });

    
  }

  public logout(): void {
    this.http.post<string>(window.location.origin + '/logout', null).subscribe(data => {
      window.location.href = data[ 'url' ];
    });
  }

  public get loggedIn(): boolean {
    return this._loggedIn;
  }

  public isAdmin(): boolean {
    return true;
  }

  public getMitarbeiterID(): number {
    return 1;
  }

  public getMitarbeiterIDAsObservable() : Observable<number> {
    return of(this.getMitarbeiterID());
  }
}
