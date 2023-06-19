import { Component, Input, OnInit } from '@angular/core';
import { ILuxDialogConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { AddMarkerDialogComponent } from '../../dialogs/add-marker-dialog/add-marker-dialog.component';
import { BuchungService } from 'src/app/services/buchung.service';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  public showNewMarker: boolean = false;
  public newMarkerX: number = 0;
  public newMarkerY: number = 0;

  dialogConfig: ILuxDialogConfig = {
    disableClose: true,
    width: '30%',
    height: 'auto',
    panelClass: [],
};
 
  
  @Input() parkingSpots: Parkplatz[];
  
  constructor(private dialogService: LuxDialogService, private buchenService: BuchungService) { }

  ngOnInit(): void {
  }
  handleMarkerClick(spot: Parkplatz) {
    // Aktionen bei Klick auf einen Marker
  }

 public getPositionStyle(spot: Parkplatz) {                        
    return {
      left: `${spot.xkoordinate}px`,
      top: `${spot.ykoordinate}px`
    };
  }

  public handleMapClick(event: MouseEvent) {
    this.showNewMarker = true;
    if(event.offsetX != 0 && event.offsetY != 0) {
      
    this.newMarkerX = event.offsetX;
    this.newMarkerY = event.offsetY;
    }
    console.log("marker" +event)
  }

  public addNewMarker() {
    const dialogRef = this.dialogService.openComponent(AddMarkerDialogComponent, this.dialogConfig);
 
    let newSpot: Parkplatz
    dialogRef.dialogClosed.subscribe((result: Parkplatz) => {
      console.log("result", this.newMarkerX)
        newSpot = result;
        newSpot.xkoordinate = this.newMarkerX;
        newSpot.ykoordinate = this.newMarkerY
         console.log("speichere", newSpot)
        
        this.buchenService.saveParkplatz(newSpot).subscribe(parkplaetze => this.parkingSpots = parkplaetze);
        
    this.showNewMarker = false;
    });
  
    
  }
}
