import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { AccountDto } from '../facade/dto/Account.dto';

@Injectable({
  providedIn: 'root'
})
export class AccountService {

  private _loggedIn: boolean = false;
  private account: AccountDto;
  private _isAdmin: boolean;

  constructor(private http: HttpClient) { }

  public setup(){
    this.http.get<any>(`${environment.apiServerUrl}/account`).subscribe(acc => {
      this.account = acc;
      this._loggedIn = true;

      this._isAdmin = this.containsRole(this.account.roles, "PA_ADMIN");
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

  private post(path, params, method='post') {
    // The rest of this code assumes you are not using a library.
    // It can be made less verbose if you use one.
    const form = document.createElement('form');
    form.method = method;
    form.action = path;
  
    for (const key in params) {
      if (params.hasOwnProperty(key)) {
        const hiddenField = document.createElement('input');
        hiddenField.type = 'hidden';
        hiddenField.name = key;
        hiddenField.value = params[key];
  
        form.appendChild(hiddenField);
      }
    }
  
    document.body.appendChild(form);
    form.submit();
  }

  public logout(): void {
    this.post(window.location.origin + "/logout", null);
  }

  public get loggedIn(): boolean {
    return this._loggedIn;
  }

  public get isAdmin(): boolean {
    return this._isAdmin;
  }

  public get name(): string {
    return this.account.name;
  }

  public getMitarbeiterID(): number {
    return this.account.mitarbeiterId;
  }
}
