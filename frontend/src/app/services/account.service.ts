import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject, of } from 'rxjs';
import { environment } from 'src/environments/environment';
import { AccountDto } from '../facade/dto/Account.dto';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _loggedIn: boolean = false;
  private account: AccountDto;
  private _isAdmin: boolean;

  private mitarbeiterIdSubject: Subject<number> = new Subject<number>();

  constructor(private http: HttpClient) { }

  public setup(){
    this.http.get<any>(`${environment.apiServerUrl}/account`).subscribe(acc => {
      console.log("#########", acc);
      this.account = acc;
      this._loggedIn = true;

      this._isAdmin = this.containsRole(this.account.roles, "PA_ADMIN");
      console.log("###", this.isAdmin);

      this.mitarbeiterIdSubject.next(this.getMitarbeiterID());
    });
  }
  
  private containsRole(roles: string[], role: string): boolean{
    for(let r of roles){
      if(r === role){
        return true;
      }
    }

    return false;
  }

  public logout(): void {
    this.http.post<string>(window.location.origin + '/logout', null).subscribe(data => {
      window.location.href = data[ 'url' ];
    });
  }

  public get loggedIn(): boolean {
    return this._loggedIn;
  }

  public get isAdmin(): boolean {
    return this._isAdmin;
  }

  private getMitarbeiterID(): number {
    return this.account.mitarbeiterId;
  }

  public getMitarbeiterIDAsObservable() : Observable<number> {
    if(this.loggedIn){
      return of(this.getMitarbeiterID());
    }
    else {
      return this.mitarbeiterIdSubject;
    }
  }
}
