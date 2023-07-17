import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILuxDialogConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Mitarbeiter } from 'src/app/facade/Mitarbeiter';
import { AccountService } from 'src/app/services/account.service';
import { ProfilServiceService } from 'src/app/services/profil-service.service';
import { DialogConfigFactory } from 'src/app/utils/dialogConfigFactory';

@Component({
  selector: 'app-page-profil',
  templateUrl: './page-profil.component.html',
  styleUrls: ['./page-profil.component.scss']
})
export class PageProfilComponent implements OnInit {
  public attr = [
    { name: 'kennzeichen', validator: this.kennzeichenValidator }
  ];

  public mitarbeiter: Mitarbeiter;

  public kennzeichenDeleteCallback: Function | undefined;

  constructor(private accountService: AccountService, private profilService: ProfilServiceService, private dialogService: LuxDialogService) { }

  ngOnInit(): void {
    let mitarbeiterID = this.accountService.getMitarbeiterID();

    this.profilService.getMitarbeiter(mitarbeiterID).subscribe((data: Mitarbeiter) => {
      this.mitarbeiter = data;
    });
  }

  public deleteKennzeichen(toDelete: any): void {
    const dialogRef = this.dialogService.open(new DialogConfigFactory().setWidth('30%').setContent("Wollen Sie das Kennzeichen wirklich löschen?").build());
    dialogRef.dialogConfirmed.subscribe(kennzeichen => {
      this.profilService.deleteKennzeichenFromMitarbeiter(this.mitarbeiter.mitarbeiterID, toDelete.kennzeichenID).subscribe(updated => {
        this.mitarbeiter = updated;
      });
    });
  }

  public saveKennzeichen(toSave: any) {
    this.profilService.createKennzeichenForMitarbeiter(this.mitarbeiter.mitarbeiterID, toSave.kennzeichen).subscribe(updated => {
      this.mitarbeiter = updated;
    });
  }

  public getName(): string {
    return this.mitarbeiter.vorname + " " + this.mitarbeiter.nachname;
  }

  private kennzeichenValidator(kennzeichen: string) {
    if (kennzeichen.match('^[A-ZÄÖÜ]{1,3}\-[ ]{0,1}[A-Z]{0,2}[0-9]{1,4}[H]{0,1}')) return undefined;
    return "Das angegebene Kennzeichen entspricht nicht der Form XXX-YY1111";
  }
}
