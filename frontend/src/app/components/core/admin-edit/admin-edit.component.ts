import { Component, OnInit } from '@angular/core';
import { ILuxFileObject } from '@ihk-gfi/lux-components';
import { Parkflaeche } from 'src/app/facade/Parkflaeche';
import { ParkflaecheDto, ParkhausParkflaecheDto } from 'src/app/facade/dto/ParkhausParkflaecheDto';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.scss']
})
export class AdminEditComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  public parkhaeuser: ParkhausParkflaecheDto[]; 
  public selectedImage: ILuxFileObject;
  public showNewParkflaeche: boolean;
  public newBezeichnung: string = "";
  public attr = [
    {name: 'bezeichnung'}
  ];
  ngOnInit(): void {
    this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
    
  }

  public saveParkflaeche(parkflaeche) {
    console.log("bla", parkflaeche);
  }

  public deleteParkflaeche(parkflaeche) {
  
  }

  
  public onSelectedFilesChange(parkflaeche: ParkflaecheDto, file: ILuxFileObject | null) {
    console.log(file);
    this.selectedImage = file;
    this.adminService.uploadImageForParkflaeche(parkflaeche.parkflaecheID, this.selectedImage.content as Blob, this.selectedImage.name).subscribe(() => {
      console.log("SADBABWDKADW");
    });
  }

  public onNewParkflaecheFileChange(file: ILuxFileObject | null) {
  }

  public addNewParkhaus() {
  }

  public addParkhaus() {
  }

  public showNewParkflaecheRow() {
    this.showNewParkflaeche = true;

  }
}
