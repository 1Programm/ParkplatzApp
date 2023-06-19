  import { Component, OnInit } from '@angular/core';
import { LuxDialogRef } from '@ihk-gfi/lux-components';
import { Parkplatztyp } from 'src/app/facade/Parkplatztyp';
import { BuchungService } from 'src/app/services/buchung.service';

@Component({
  selector: 'app-add-marker-dialog',
  templateUrl: './add-marker-dialog.component.html',
  styleUrls: ['./add-marker-dialog.component.scss']
})
export class AddMarkerDialogComponent implements OnInit {

  public typen: Parkplatztyp[];
  public selected: Parkplatztyp;
  public nummer: number;
  constructor(private buchenService: BuchungService, public luxDialogRef: LuxDialogRef) { }

  ngOnInit(): void {
    this.buchenService.getParkplatztypen().subscribe(data => {
      this.typen = data;
      this.selected = data[0];
    });
  }

}
