import { Injectable } from '@angular/core';
import { ServiceBase } from './service-utils';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MapService extends ServiceBase {

  constructor(private http: HttpClient) {
    super();
  }

  public getImageForParkflaeche(parkflaecheID: number): Observable<any> {
    let options: Object = {
      responseType: "blob"
    }

    return this.wrapRetryAndCatchError(
      this.http.get<ArrayBuffer>(`${environment.apiServerUrl}/map/parkflaeche/${parkflaecheID}`, options)
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
      this.http.post<void>(`${environment.apiServerUrl}/map/parkflaeche/${parkflaecheID}/upload`, imageFormData)
    );
  }

}
