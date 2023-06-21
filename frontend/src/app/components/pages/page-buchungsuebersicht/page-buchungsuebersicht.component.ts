import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LuxDialogService, LuxSnackbarService } from '@ihk-gfi/lux-components';
import { Buchung } from 'src/app/facade/Buchung';
import { Kennzeichen } from 'src/app/facade/Kennzeichen';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { AccountService } from 'src/app/services/account.service';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';
import { DialogConfigFactory } from 'src/app/utils/dialogConfigFactory';

@Component({
  selector: 'app-buchungsuebersicht',
  templateUrl: './page-buchungsuebersicht.component.html',
  styleUrls: ['./page-buchungsuebersicht.component.scss']
})
export class PageBuchungsuebersichtComponent implements OnInit {

  public buchungen: BuchungDto[];
  public kennzeichen: Kennzeichen[];
  public selected: Kennzeichen;

  constructor(
    private accountService: AccountService,
    private buchungenService: BuchungsuebersichtService,
    private snackbar: LuxSnackbarService,
    private luxDialogService: LuxDialogService
  ) { }

  ngOnInit(): void {
      // Abrufen der Buchungen für den Mitarbeiter
      this.buchungenService.getBuchungenForMitarbeiter().subscribe((data: BuchungDto[]) => {
        this.buchungen = data;
      });

      // Abrufen der Kennzeichen für den Mitarbeiter
      this.buchungenService.getKennzeichenForMitarbeiter().subscribe((data: Kennzeichen[]) => {
        this.kennzeichen = data;
      });
    
  }

  saveKennzeichenForBuchung(kennzeichen: Kennzeichen, buchung: BuchungDto) {
    // Speichern der Kennzeichenänderung für die Buchung
    // Abrufen des ausgewählten Kennzeichens für die Buchung
    this.buchungenService.saveKennzeichenForBuchung(buchung.buchungID, kennzeichen.kennzeichenID).subscribe(buchungen => {
      this.buchungen = buchungen;
      this.snackbar.openText("Kennzeichenänderung wurde gespeichert.", 2000);
    });
  }

  deleteBuchung(buchung: BuchungDto) {
    // Öffnen eines Dialogs zur Bestätigung der Buchungslöschung 
    const dialogRef = this.luxDialogService.open(new DialogConfigFactory().setWidth('30%').setContent("Wollen Sie die Buchung wirklich löschen?").build());
    dialogRef.dialogConfirmed.subscribe(() => {
      // Löschen der Buchung
      this.buchungenService.deleteBuchungFromMitarbeiter(buchung.buchungID).subscribe(updated => {
        this.buchungen = updated;
      });
    });
  }

  isDateBeforeToday(date): boolean {
    // Überprüfen, ob das Datum vor dem heutigen Datum liegt
    return new Date(date).valueOf() > new Date().valueOf();
  }

  getTitle(datum: Date): string {
    // Formatieren des Datums im gewünschten Format
    return formatDate(datum, 'dd/MM/YYYY', "de-DE");
  }

  getSelected(buchung: BuchungDto) {
    // Abrufen des ausgewählten Kennzeichens für die Buchung
    return this.kennzeichen.find(item => item.kennzeichenID === buchung.kennzeichen.kennzeichenID); 
  }

}
