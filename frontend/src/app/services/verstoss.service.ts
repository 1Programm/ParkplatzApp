import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, retry, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { VerstossDto } from '../facade/dto/verstoss.dto';

@Injectable({
  providedIn: 'root'
})
export class VerstossService {

  constructor(private http: HttpClient) { }


  public saveKennzeichenForBuchung(verstossDto: VerstossDto) {
    return this.http.post<VerstossDto>(`${environment.apiServerUrl}/verstoss/update`, verstossDto)
    .pipe(
      retry(1),
      catchError(this.handleError)
    )
  }

  // Error handling
  handleError(error: any) {
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      // Get client-side error
      errorMessage = error.error.message;
    } else {
      // Get server-side error
      errorMessage = `Error Code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
