import { Component, Input, OnChanges, OnInit, SimpleChange, SimpleChanges } from '@angular/core';
import { ILuxDialogConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { AddMarkerDialogComponent } from '../../dialogs/add-marker-dialog/add-marker-dialog.component';
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

  dialogConfig: ILuxDialogConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: [],
  };

  alleParkplaetze: Parkplatz[];
  parkplaetzeForDate: ParkplatzMitStatusDto[];
  isAdmin: boolean;

  constructor(private dialogService: LuxDialogService, private buchungService: BuchungService, private accountService: AccountService) { }

  ngOnInit(): void {
    this.reloadData();
  }

  ngOnChanges() {
    this.reloadData()
  }

  reloadData() {
    this.isAdmin = this.accountService.isAdmin();
    
    if(this.isAdmin) {
        this.buchungService.getParkplaetzeOfParkflaeche(this.parkflaecheID).subscribe( data => {
          this.alleParkplaetze = data;
         });
      } else {
      
        this.buchungService.getParkplaetzeOfParkflaecheAndDate(this.parkflaecheID, new Date(this.date).toLocaleDateString()).subscribe( data => {
          this.parkplaetzeForDate = data;
      });
    
    }
  }
  handleMarkerClick(event: MouseEvent, spot: Parkplatz) {
    event.stopPropagation();
    const dialogRef = this.dialogService.openComponent(AddMarkerDialogComponent, this.dialogConfig, spot);
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

 public getPositionStyle(spot: Parkplatz) {                        
    return {
      left: `${spot.xkoordinate}px`,
      top: `${spot.ykoordinate}px`
    };
  }

  public handleMapClick(event: MouseEvent) {
  this.showNewMarker = true;
  const container = event.currentTarget as HTMLElement;
  const boundingRect = container.getBoundingClientRect();
    
  this.newMarkerX=event.clientX - boundingRect.left;
  this.newMarkerY =event.clientY - boundingRect.top;

  }

  public addNewMarker() {
    
    const dialogRef = this.dialogService.openComponent(AddMarkerDialogComponent, this.dialogConfig);
 
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
}
