import { Component, OnInit } from '@angular/core';
import { LuxDialogRef } from '@ihk-gfi/lux-components';

@Component({
  selector: 'app-kennzeichen-hinzufuegen-dialog',
  templateUrl: './kennzeichen-hinzufuegen-dialog.component.html',
  styleUrls: ['./kennzeichen-hinzufuegen-dialog.component.scss']
})
export class KennzeichenHinzufuegenDialogComponent implements OnInit {

    kennzeichen: string;
    constructor(public luxDialogRef: LuxDialogRef) {}
  
    ngOnInit(): void {
    }
  
  
    
  
}
