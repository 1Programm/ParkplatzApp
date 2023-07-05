import { Component, EventEmitter, Input, OnChanges, OnInit, Output } from '@angular/core';
import { ILuxDialogPresetConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { ParkflaecheAuswahlDto } from 'src/app/facade/dto/parkflaeche-auswahl.dto';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { AccountService } from 'src/app/services/account.service';
import { BuchungService } from 'src/app/services/buchung.service';
import { MarkerDialogComponent } from '../../dialogs/marker-dialog/marker-dialog.component';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit, OnChanges {
  public showNewMarker = false;
  public newMarkerX = 0;
  public newMarkerY = 0;

  @Input() date: Date;
  @Input() parkflaecheID: number;
  @Output() parkplatzSelected = new EventEmitter<Parkplatz>();

  dialogConfig: ILuxDialogPresetConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: []
  };

  alleParkplaetze: Parkplatz[];
  parkplaetzeForDate: ParkplatzMitStatusDto[];
  isAdmin = this.accountService.isAdmin;

  constructor(
    private dialogService: LuxDialogService,
    private buchungService: BuchungService,
    private accountService: AccountService,
    private adminService: AdminService
  ) {}

  ngOnInit(): void {
    this.reloadData();
  }

  ngOnChanges(): void {
    this.reloadData();
  }

  reloadData(): void {
    if(this.date === undefined) return;
    console.log(this.date);
    
    if (this.isAdmin) {
      this.buchungService.getParkplaetzeOfParkflaeche(this.parkflaecheID).subscribe((data) => {
        this.alleParkplaetze = data;
      });
    } else {
      // const datum = new Date(this.date).toLocaleDateString();
      console.log(this.date, this.date.toLocaleDateString());

      this.buchungService.getParkplaetzeOfParkflaecheAndDate(this.parkflaecheID, this.date).subscribe((data) => {
        this.parkplaetzeForDate = data;
      });
    }
  }

  
  //wird ausgeführt, wenn der User auf einen Marker klickt
  handleUserMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);

    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
      if (result != null) {
        this.parkplatzSelected.emit(result);
      }
      this.showNewMarker = false;
    });
  }

  //wird ausgeführt, wenn der Admin auf einen bestehenden Marker klickt
  handleAdminMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);
    let newSpot: Parkplatz;

    dialogRef.dialogClosed.subscribe((result) => {
      if (result != null) {
        if (result.nummer == undefined) {
          this.adminService.deleteParkplatz(result).subscribe((parkplatz) => {
            if (parkplatz != null) {
              this.reloadData();
            }
          });
        } else {
          newSpot = result;
          this.adminService.saveParkplatz(newSpot, this.parkflaecheID).subscribe((parkplaetze) => {
            this.alleParkplaetze = parkplaetze;
          });
        }
      }
      this.showNewMarker = false;
    });
  }

  //wird ausgeführt, wenn der Admin auf die Karte klickt; fügt einen temporären Marker hinzu
  handleMapClick(event: MouseEvent): void {
    this.showNewMarker = true;
    const container = event.currentTarget as HTMLElement;
    const boundingRect = container.getBoundingClientRect();
    this.newMarkerX = event.clientX - boundingRect.left;
    this.newMarkerY = event.clientY - boundingRect.top;
  }

  //wird ausgeführt, wenn der Admin auf den temporären neuen Marker klickt (noch nicht ausgefüllt)
  handleNewMarkerClicked(event): void {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig);
    let newSpot: Parkplatz;

    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
      newSpot = result;
      newSpot.xkoordinate = this.newMarkerX;
      newSpot.ykoordinate = this.newMarkerY;
      this.adminService.saveParkplatz(newSpot, this.parkflaecheID).subscribe((parkplaetze) => {
        this.alleParkplaetze = parkplaetze;
      });

      this.showNewMarker = false;
    });
  }

  getPositionStyle(spot: Parkplatz): any {
    return {
      left: `${spot.xkoordinate}px`,
      top: `${spot.ykoordinate}px`
    };
  }

  getMarkerColor(marker: any): string {
    if (marker.status === 'FREI') {
      return 'green';
    }
    return 'red';
  }

  toParkplatzMitStatusDto(spot: Parkplatz): ParkplatzMitStatusDto {
    return {
      parkplatz: spot,
      status: undefined
    };
  }
}
