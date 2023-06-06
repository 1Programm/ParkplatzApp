import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProfildatenDto } from '../facade/dto/ProfildatenDto';
@Injectable({
  providedIn: 'root'
})
export class ProfilServiceService {

  constructor(private http: HttpClient) { }

  getProfildaten(id:number): Observable<ProfildatenDto> {
    return this.http.get<any>(`{$this.apiServerUrl}/profil/`)
  }
}
