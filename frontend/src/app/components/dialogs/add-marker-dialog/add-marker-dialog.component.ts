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

  protected typen: Parkplatztyp[];
  protected preiskategorie: Preiskategorie[];
  protected selectedTyp: Parkplatztyp;
  protected selectedKategorie: Preiskategorie;
  protected nummer: string;
  protected nummerFnArr = [Validators.pattern(/^[\d]{1,3}$/)];
  protected parkplatzFormGroup: FormGroup;

  constructor(private buchenService: BuchungService, public luxDialogRef: LuxDialogRef) { }

  ngOnInit(): void {
    this.buchenService.getParkplatztypen().subscribe(data => {
      this.typen = data;
      this.selectedTyp = data[0];
    });

    this.buchenService.getPreiskategorien().subscribe(preiskategorie => {
      this.preiskategorie = preiskategorie;
      this.selectedKategorie = preiskategorie[0];
    });
  }

  protected submitDialog() {
    console.log("bla", this.nummer)
    let parkplatz: Parkplatz = {
      parkplatzID: undefined,
      nummer: this.nummer,
      xkoordinate: 0,
      ykoordinate: 0,
      parkplatztyp: this.selectedTyp,
      preiskategorie: this.selectedKategorie
    }
    this.luxDialogRef.closeDialog(parkplatz);
  }

  protected nummerErrorCallback = (value: any, errors: LuxValidationErrors) => {
    if (errors['pattern']) return 'Die Parkplatznummer darf nur Zahlen enthalten';
    return undefined;
  }
}
