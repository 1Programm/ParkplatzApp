import { Component, Input, OnInit } from '@angular/core';
import { ILuxDialogConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { AddMarkerDialogComponent } from '../../dialogs/add-marker-dialog/add-marker-dialog.component';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements OnInit {

  showNewMarker: boolean = false;
  newMarkerX: number = 0;
  newMarkerY: number = 0;

  dialogConfig: ILuxDialogConfig = {
    disableClose: true,
    width: 'auto',
    height: 'auto',
    panelClass: [],
};
 
// Das hier könnte auch jedes andere Objekt sein, Hauptsache ExampleDialogComponent kann dieses Objekt interpretieren
data: string = 'Im nächsten Fenster können Sie den Vorgang widerrufen.';
 
  
  @Input() parkingSpots: Parkplatz[];
  
  constructor(private dialogService: LuxDialogService) { }

  ngOnInit(): void {
  }
  handleMarkerClick(spot: Parkplatz) {
    // Aktionen bei Klick auf einen Marker
  }

  openDialog() {
    const dialogRef = this.dialogService.openComponent(AddMarkerDialogComponent, this.dialogConfig, this.data);
 
    dialogRef.dialogClosed.subscribe((result: any) => {
        console.log('dialogClosed', result);
    });
}
  getPositionStyle(spot: Parkplatz) {
    console.log("blaa", spot)
    return {
      left: `${spot.xkoordinate}px`,
      top: `${spot.ykoordinate}px`
    };
  }

  handleMapClick(event: MouseEvent) {
    console.log("blar")
    this.showNewMarker = true;
    this.newMarkerX = event.offsetX;
    this.newMarkerY = event.offsetY;
  }

  addNewMarker() {
    this.openDialog();
  
    const newSpot: Parkplatz = {
      parkplatzID: null,
      nummer: 'New Marker',
      xkoordinate: this.newMarkerX,
      ykoordinate: this.newMarkerY,
      parkplatztyp: null,
      preiskategorie: null
    };
    this.parkingSpots.push(newSpot);
    this.showNewMarker = false;
  }
}
