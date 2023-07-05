import { Injectable } from '@angular/core';
import { ServiceBase } from './service-utils';
import { Observable } from 'rxjs';
import { ParkflaecheAuswahlDto } from '../facade/dto/parkflaeche-auswahl.dto';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';
import { ParkflaecheDto, ParkhausParkflaecheDto } from '../facade/dto/ParkhausParkflaecheDto';
import { Parkplatz } from '../facade/Parkplatz';
import { Parkflaeche } from '../facade/Parkflaeche';

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

  public saveParkplatz(parkplatz: Parkplatz, parkflaecheID: number): Observable<Parkplatz[]> {
    return this.wrapRetryAndCatchError(
      this.http.post<Parkplatz[]>(`${environment.apiServerUrl}/admin/parkplatz/${parkflaecheID}`, parkplatz)
    );
  }

  public deleteParkplatz(parkplatzID: number): Observable<Parkplatz> {
    return this.wrapRetryAndCatchError(this.http.delete<Parkplatz>(`${environment.apiServerUrl}/admin/parkplatz/${parkplatzID}`)
    );
  }

  public deleteParkflaeche(parkflaecheID: number, parkhausID: number): Observable<void> {
    return this.wrapRetryAndCatchError(this.http.delete<void>(`${environment.apiServerUrl}/admin/parkflaeche/${parkflaecheID}/parkhaus/${parkhausID}`)
    );
  }

  public saveParkflaeche(parkhausID: number, parkflaeche: ParkflaecheDto): Observable<Parkflaeche> {
    return this.wrapRetryAndCatchError(
      this.http.post<Parkflaeche>(`${environment.apiServerUrl}/admin/parkflaeche/${parkhausID}`, parkflaeche)
    );
  }

  public getImageForParkflaeche(parkflaecheID: number): Observable<any> {
    let options: Object = {
      responseType: "blob"
    }

    return this.wrapRetryAndCatchError(
      this.http.get<ArrayBuffer>(`${environment.apiServerUrl}/admin/parkflaeche/${parkflaecheID}`, options)
    );
  }

  public uploadImageForParkflaeche(parkflaecheID: number, image: Blob, fileName: string): Observable<void> {
    // if(!(image instanceof Blob)){
    //   // image = new Blob([image]);
    //   image = new File([image], fileName, {type: fileType});
    // }
    
    console.log("A", parkflaecheID, image, fileName);

    let newFileName = fileName.replace(/\u00fc/g, 'ue');
    newFileName = newFileName.replace(/\u00dc/g, 'Ue');
    newFileName = newFileName.replace(/\u00e4/g, 'ae');
    newFileName = newFileName.replace(/\u00c4/g, 'Ae');
    newFileName = newFileName.replace(/\u00f6/g, 'oe');
    newFileName = newFileName.replace(/\u00d6/g, 'Oe');
    newFileName = newFileName.replace(/\u00df/g, 'ss');

    const imageFormData = new FormData();
    imageFormData.append('image', image, newFileName);
    console.log("B", imageFormData);

    
    return this.wrapRetryAndCatchError(
      this.http.post<void>(`${environment.apiServerUrl}/admin/parkflaeche/${parkflaecheID}/upload`, imageFormData)
    );
  }

}
