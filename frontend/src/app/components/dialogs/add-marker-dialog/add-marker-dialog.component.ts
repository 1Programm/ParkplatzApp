import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { LuxDialogRef, LuxValidationErrors } from '@ihk-gfi/lux-components';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { Parkplatztyp } from 'src/app/facade/Parkplatztyp';
import { Preiskategorie } from 'src/app/facade/Preiskategorie';
import { BuchungService } from 'src/app/services/buchung.service';

@Component({
  selector: 'app-add-marker-dialog',
  templateUrl: './add-marker-dialog.component.html',
  styleUrls: ['./add-marker-dialog.component.scss']
})
export class AddMarkerDialogComponent implements OnInit {

  typen: Parkplatztyp[];
  preiskategorie: Preiskategorie[];
  selectedTyp: Parkplatztyp;
  selectedKategorie: Preiskategorie;
  nummer: string;
  nummerFnArr = [Validators.pattern(/^[\d]{1,3}$/), Validators.required];
  parkplatzFormGroup: FormGroup;
  parkplatz: Parkplatz;

  constructor(private buchenService: BuchungService, public luxDialogRef: LuxDialogRef) { }

  ngOnInit(): void {
    this.parkplatz = this.luxDialogRef.data;
    this.nummer = this.parkplatz?.nummer;
    this.initializeTypen();
    this.initializePreiskategorien();
  }

  private initializeTypen(): void {
    this.buchenService.getParkplatztypen().subscribe(data => {
      this.typen = data;
      this.selectedTyp = this.typen[0];
      if (this.parkplatz) {
        this.selectedTyp = this.typen.find(item => item.parkplatztypID === this.parkplatz.parkplatztyp.parkplatztypID);
      }
    });
  }

  private initializePreiskategorien(): void {
    this.buchenService.getPreiskategorien().subscribe(preiskategorie => {
      this.preiskategorie = preiskategorie;
      this.selectedKategorie = preiskategorie[0];
      if (this.parkplatz) {
        this.selectedKategorie = this.preiskategorie.find(item => item.kategorieID === this.parkplatz.preiskategorie.kategorieID);
      }
    });
  }

  submitDialog(): void {
    const p: Parkplatz = {
      parkplatzID: this.parkplatz ? this.parkplatz.parkplatzID : null,
      nummer: this.nummer,
      xkoordinate: this.parkplatz ? this.parkplatz.xkoordinate : 0,
      ykoordinate: this.parkplatz ? this.parkplatz.ykoordinate : 0,
      parkplatztyp: this.selectedTyp,
      preiskategorie: this.selectedKategorie
    };

    this.luxDialogRef.closeDialog(p);

  }

  deleteParkplatz(){
    
}
  nummerErrorCallback = (value: any, errors: LuxValidationErrors): string | undefined => {
    if (errors['pattern']) return 'Die Parkplatznummer darf nur Zahlen enthalten';
    return undefined;
  }
}