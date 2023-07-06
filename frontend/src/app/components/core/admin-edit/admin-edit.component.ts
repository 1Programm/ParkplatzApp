import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ILuxDialogPresetConfig, ILuxFileActionConfig, ILuxFileObject, LuxDialogRef, LuxDialogService } from '@ihk-gfi/lux-components';
import { Parkflaeche } from 'src/app/facade/Parkflaeche';
import { ParkflaecheDto, ParkhausParkflaecheDto } from 'src/app/facade/dto/ParkhausParkflaecheDto';
import { AdminService } from 'src/app/services/admin.service';
import { EditParkhausDialogComponent } from '../../dialogs/edit-parkhaus-dialog/edit-parkhaus-dialog.component';
import { ParkhausEditierenDto } from 'src/app/facade/dto/ParkhausEditierenDto';
import { DialogConfigFactory } from 'src/app/utils/dialogConfigFactory';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.scss']
})
export class AdminEditComponent implements OnInit {
  @Output() selectedParkhausChanged = new EventEmitter<boolean>();

  public parkhaeuser: ParkhausParkflaecheDto[];
  public selectedImage: ILuxFileObject | undefined;
  public showNewParkflaeche: boolean;
  public newBezeichnung: string = "";
  public expanded: boolean[] = [];
  public deleteConfig: ILuxFileActionConfig = {
    disabled: true,
    hidden: true,
    iconName: 'fas fa-trash',
    label: 'Löschen'
  };

  public editParkhausDialogConfig: ILuxDialogPresetConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: []
  };

  public attr = [
    { name: 'bezeichnung' }
  ];

  constructor(private adminService: AdminService, private dialogService: LuxDialogService) { }

  ngOnInit(): void {
    this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
      this.parkhaeuser = parkhaeuser;
    });
  }

  public saveParkflaeche(parkhaus: any, parkflaeche: any) {
    this.adminService.saveParkflaeche(parkhaus.parkhausID, parkflaeche).subscribe(() => {
      this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
        this.parkhaeuser = parkhaeuser;
      });
    });

    this.selectedParkhausChanged.emit(true);
  }

  public deleteParkflaeche(parkhaus: any, parkflaeche: any) {
    this.adminService.deleteParkflaeche(parkflaeche.parkflaecheID, parkhaus.parkhausID).subscribe(() => {
      this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
        this.parkhaeuser = parkhaeuser;
      });

      this.selectedParkhausChanged.emit(true);
    });
  }

  public saveNewParkflaeche(parkhaus: any) {
    let toSave: ParkflaecheDto = {
      parkflaecheID: undefined,
      bezeichnung: this.newBezeichnung,
      image: null
    };

    this.adminService.saveParkflaeche(parkhaus.parkhausID, toSave).subscribe((parkflaeche) => {
      this.adminService.uploadImageForParkflaeche(parkflaeche.parkflaecheID, this.selectedImage?.content as Blob, this.selectedImage?.name).subscribe(() => {
        this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
          this.parkhaeuser = parkhaeuser;
        });

        this.showNewParkflaeche = false;
        this.newBezeichnung = "";
        this.selectedImage = undefined;
      });

      this.selectedParkhausChanged.emit(true);
    });
  }

  public onSelectedFilesChange(parkflaeche: ParkflaecheDto, file: ILuxFileObject) {
    this.adminService.uploadImageForParkflaeche(parkflaeche.parkflaecheID, file.content as Blob, file.name).subscribe(() => {});
  }

  public saveParkhaus(parkhausID?: number) {
    if (parkhausID === undefined) {
      let toSave = {
        parkhausID: undefined,
        bezeichnung: "",
        strasse: "",
        hausnummer: 0,
        plz: 0,
        ort: ""
      };
      this.openParkhausDialog(toSave);
    } else {
      this.adminService.getParkhaus(parkhausID).subscribe(parkhaus => {
        this.openParkhausDialog(parkhaus);
      });
    }
  }

  public openParkhausDialog(parkhaus: ParkhausEditierenDto) {
    const dialogRef = this.dialogService.openComponent(EditParkhausDialogComponent, this.editParkhausDialogConfig, parkhaus);
    dialogRef.dialogClosed.subscribe((result) => {
      if (result != null) {
        this.adminService.saveParkhaus(parkhaus).subscribe(() => {
          this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
            this.parkhaeuser = parkhaeuser;
          });
        });

        this.selectedParkhausChanged.emit(true);
      }
    });
  }

  public deleteParkhaus(parkhausID: number) {
    // Öffnen eines Dialogs zur Bestätigung der Buchungslöschung
    const dialogRef = this.dialogService.open(new DialogConfigFactory().setWidth('30%').setContent("Wollen Sie das Parkhaus inkl Parkhaus und Parkplätze wirklich löschen?").build());
    dialogRef.dialogConfirmed.subscribe(() => {
      this.adminService.deleteParkhaus(parkhausID).subscribe(() => {
        this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {
          this.parkhaeuser = parkhaeuser;
          this.selectedParkhausChanged.emit(true);
        });
      });
    });
  }

  public showNewParkflaecheRow() {
    this.showNewParkflaeche = true;
  }

  public isFilledOut() {
    return this.selectedImage === undefined || this.newBezeichnung === "";
  }
}
