import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
import { ILuxFileObject } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { AdminService } from 'src/app/services/admin.service';
import { BuchungService } from 'src/app/services/buchung.service';
import { MapService } from 'src/app/services/map.service';
import { ImageUtils } from 'src/app/utils/image.utils';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-home',
  templateUrl: './page-home.component.html',
  styleUrls: ['./page-home.component.scss']
})
export class PageHomeComponent implements OnInit {

  public parkflaecheID: string;
  public imageID: string;
  public image: any;


  public selected: ILuxFileObject;

  constructor(private mapService: MapService, private http: HttpClient, private adminService: AdminService ) {
    // this.loadFileFromAssets();
  }

  ngOnInit(): void {
  }

  public loadImage(){
    this.adminService.getImageForParkflaeche(+this.parkflaecheID).subscribe(data => {
      ImageUtils.readAsDataUrl(data).subscribe(image => {
        this.image = image;
      })
    })
  }

  public loadImageByImageID(){
    let options: Object = {
      responseType: "blob"
    }

    this.http.get<ArrayBuffer>(`${environment.apiServerUrl}/image/${this.imageID}`, options).subscribe(data => {
      ImageUtils.readAsDataUrl(data).subscribe(image => {
        this.image = image;
      })
    })
  }
  
  /**
    * Für das Beispiel laden wir eine Datei aus dem Assets-Ordner.
    * Voraussetzung: assets-Ordner enthält den Unterordner "png" und die Datei "example.png".
    */
  public loadFileFromAssets() {
    this.http.get('assets/png/example.png', {responseType: 'blob'})
      .subscribe((response: Blob) => {
        const file = <any>response;
        file.name = 'example.png';
        file.lastModifiedDate = new Date();
        const reader = new FileReader();
        reader.onload = (fileData: any) => {
          this.selected = {name: 'example.png', type:'image/png', content: fileData.target.result};
        };
        reader.readAsDataURL(file);
      });
  }
  
  public onSelectedFilesChange(file: ILuxFileObject | null) {
    console.log(file);
    this.selected = file;
  }

  public uploadImage(){
    console.log("1", this.selected);
    console.log("2", this.selected.content);

    this.adminService.uploadImageForParkflaeche(+this.parkflaecheID, this.selected.content as Blob, this.selected.name).subscribe(() => {
      console.log("SADBABWDKADW");
    });
  }

  public uploadImage2(event){
    let file = event.target.files[0];
    this.adminService.uploadImageForParkflaeche(+this.parkflaecheID, file, file.name).subscribe(() => {
      console.log("SADBABWDKADW");
    });
  }

}
