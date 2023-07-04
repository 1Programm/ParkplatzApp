import { Component, OnInit } from '@angular/core';
import { ILuxFileActionConfig, ILuxFileObject } from '@ihk-gfi/lux-components';
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
  public selectedImage: ILuxFileObject | undefined;
  public showNewParkflaeche: boolean;
  public newBezeichnung: string = "";
  public expanded: boolean[] = [];
  public deleteConfig: ILuxFileActionConfig	= {
    disabled: true,
    hidden: true,
    iconName: 'fas fa-trash',
    label: 'Löschen'
  }

  public attr = [
    {name: 'bezeichnung'}
  ];
  ngOnInit(): void {
    this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
    
  }

  public saveParkflaeche(parkhaus, parkflaeche) {
    this.adminService.saveParkflaeche(parkhaus.parkhausID, parkflaeche).subscribe(() => {
      this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
    });
  }

  public deleteParkflaeche(parkhaus, parkflaeche) {
    this.adminService.deleteParkflaeche(parkflaeche.parkflaecheID, parkhaus.parkhausID, ).subscribe(() => {
      this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
    });


    console.log("expanded ", this.expanded)
  }

  public saveNewParkflaeche(parkhaus) {
    let toSave: ParkflaecheDto = {
      parkflaecheID: undefined,
      bezeichnung: this.newBezeichnung,
      image: null
    }
      this.adminService.saveParkflaeche(parkhaus.parkhausID, toSave).subscribe((parkflaeche) => {
        
        this.adminService.uploadImageForParkflaeche(parkflaeche.parkflaecheID, this.selectedImage?.content as Blob, this.selectedImage?.name).subscribe(() => {
          this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
          this.showNewParkflaeche = false;
          this.newBezeichnung = "";
          this.selectedImage = undefined;
        });
        
      });
  }
  
  public onSelectedFilesChange(parkflaeche: ParkflaecheDto, file: ILuxFileObject) {
    this.adminService.uploadImageForParkflaeche(parkflaeche.parkflaecheID, file.content as Blob, file.name).subscribe(() => {
    });
  }

  public addNewParkhaus() {
  }

  public addParkhaus() {
  }

  public showNewParkflaecheRow() {
    this.showNewParkflaeche = true;

  }

  public isFilledOut() {
    return this.selectedImage === undefined || this.newBezeichnung === "";
  }
}
