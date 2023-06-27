import { Component, ElementRef, Input, OnChanges, OnInit, Output, SimpleChange, SimpleChanges, ViewChild } from '@angular/core';
import { ILuxDialogConfig, ILuxDialogPresetConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { MarkerDialogComponent } from '../../dialogs/marker-dialog/marker-dialog.component';
import { BuchungService } from 'src/app/services/buchung.service';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { AccountService } from 'src/app/services/account.service';
import { ParkflaecheAuswahlDto } from 'src/app/facade/dto/parkflaeche-auswahl.dto';
import { string32 } from 'pdfjs-dist/types/src/shared/util';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  public showNewMarker: boolean = false;
  public newMarkerX: number = 0;
  public newMarkerY: number = 0;

  @Input()
  public date: string;

  @Input()
  public parkflaecheID: number;

  @Output()
  buchung: any;

  dialogConfig: ILuxDialogPresetConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: [],
    
  };

  alleParkplaetze: Parkplatz[];
  parkplaetzeForDate: ParkplatzMitStatusDto[];
  isAdmin: boolean = this.accountService.isAdmin;

  constructor(private dialogService: LuxDialogService, private buchungService: BuchungService, private accountService: AccountService) { }

  ngOnInit(): void {
    this.reloadData();
  }

  ngOnChanges() {
    this.reloadData()
  }

  reloadData() {
    
    if(this.isAdmin) {
        this.buchungService.getParkplaetzeOfParkflaeche(this.parkflaecheID).subscribe( data => {
          this.alleParkplaetze = data;
         });
      } else {
        let datum = new Date(this.date).toLocaleDateString();

        this.buchungService.getParkplaetzeOfParkflaecheAndDate(this.parkflaecheID, datum).subscribe( data => {
          this.parkplaetzeForDate = data;
      });
    
    }
  }
  handleAdminMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto) {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);
    let newSpot: Parkplatz
    
    dialogRef.dialogClosed.subscribe((result) => {
      if(result != null) {
        if(result.nummer == undefined) {
          this.buchungService.deleteParkplatz(result).subscribe()

        } else {
          
        newSpot = result;
        this.buchungService.saveParkplatz(newSpot, this.parkflaecheID).subscribe(parkplaetze => this.alleParkplaetze = parkplaetze);
        }
      }
    this.showNewMarker = false;
    });
  }

  handleUserMarkerClick(event: MouseEvent, spot: ParkplatzMitStatusDto) {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig, spot);
    let newSpot: Parkplatz
    
    dialogRef.dialogClosed.subscribe((result) => {
      if(result != null) {
       {
        this.buchung.emit(result);
        }
      }
    this.showNewMarker = false;
    });
  }

 public getPositionStyle(spot: Parkplatz) {                        
    return {
      left: `${spot.xkoordinate}px`,
      top: `${spot.ykoordinate}px`,
      
    };
  }

  public handleMapClick(event: MouseEvent) {
  this.showNewMarker = true;
  const container = event.currentTarget as HTMLElement;
  const boundingRect = container.getBoundingClientRect();
  this.newMarkerX = event.clientX - boundingRect.left;
 this.newMarkerY = event.clientY - boundingRect.top;

  }

  public addNewMarker() {
    const dialogRef = this.dialogService.openComponent(MarkerDialogComponent, this.dialogConfig);
    let newSpot: Parkplatz
    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
        newSpot = result;
        newSpot.xkoordinate = this.newMarkerX;
        newSpot.ykoordinate = this.newMarkerY
        this.buchungService.saveParkplatz(newSpot, this.parkflaecheID).subscribe(parkplaetze => this.alleParkplaetze = parkplaetze);
        
    this.showNewMarker = false;
    });
  
    
  }

  public getMarkerColor(marker) {
    if(marker.status =="FREI")
      return 'green'  
    return 'red'
  }

  toParkplatzMitStatusDto(spot: Parkplatz): ParkplatzMitStatusDto {
    let res: ParkplatzMitStatusDto = {
      parkplatz: spot,
      status: undefined
    }

    return res;
  }
}
