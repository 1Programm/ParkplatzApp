import { Component, OnInit } from '@angular/core';
import { VerstossDto } from 'src/app/facade/dto/verstoss.dto';
import { AccountService } from 'src/app/services/account.service';
import { VerstossService } from 'src/app/services/verstoss.service';
import { VerstossStatus } from 'src/app/utils/verstossStatus.enum';
import { DateUtils } from 'src/app/utils/date.utils';
import { LuxSnackbarService } from '@ihk-gfi/lux-components';

@Component({
  selector: 'app-page-verstoss',
  templateUrl: './page-verstoss.component.html',
  styleUrls: ['./page-verstoss.component.scss']
})
export class PageVerstossComponent implements OnInit {

  public selectedDatum: string = DateUtils.toVisibleString(new Date());
  public bemerkung: string = '';
  public mitarbeiterVerstoesse: VerstossDto[];
  public verstoesse: VerstossDto[];
  public isAdmin: boolean = this.accountService.isAdmin;
  public statusAuswahl: VerstossStatus[] = [VerstossStatus.IN_BEARBEITUNG , VerstossStatus.ABGESCHLOSSEN];

  private verstoss: VerstossDto = {
    mitarbeiterID: 1,
    meldeID: 1,
    datum: new Date(),
    bemerkung: '',
    status: VerstossStatus.IN_BEARBEITUNG
  };

  constructor(private accountService: AccountService, private verstossService: VerstossService, private snackbarService: LuxSnackbarService) { }

  ngOnInit(): void {
    this.verstoss.mitarbeiterID = this.accountService.getMitarbeiterID();
    this.getVertoesseForMitarbeiter(this.verstoss.mitarbeiterID);
    this.getAllVerstoesse();
  }

  public speichernVerstoss(): void {
    this.verstoss.bemerkung = this.bemerkung;
    this.verstoss.datum = new Date(this.selectedDatum);
    this.verstossService.speichernVerstoss(this.verstoss).subscribe(() => {
      this.selectedDatum = '';
      this.bemerkung = '';
      this.getVertoesseForMitarbeiter(this.verstoss.mitarbeiterID);
    }); 
  }

  private getVertoesseForMitarbeiter(mitarbeiterID: number): void {
    this.verstossService.getVertoesseForMitarbeiter(mitarbeiterID).subscribe(data => {
      this.mitarbeiterVerstoesse = data;
    });
  }

  private getAllVerstoesse() : void {
    this.verstossService.getAllVerstoesse().subscribe(data => {
      this.verstoesse = data;
    });
  }

  public changeDateFormat(datum: Date): string {
    return DateUtils.toVisibleString(datum);
  }

  public getZugelasenesDatum(): string {
    return DateUtils.toTechnicalString(new Date());
  }

  changeStatusForVerstoss(status: VerstossStatus, verstoss: VerstossDto) {
    if(status == VerstossStatus.IN_BEARBEITUNG) {
      status = VerstossStatus.IN_BEARBEITUNG;
    } else {
      status = VerstossStatus.ABGESCHLOSSEN;
    }
    verstoss.status = status;
    this.verstossService.changeStatusForVerstoss(verstoss).subscribe(() => {
      this.snackbarService.openText("Statusänderung wurde gespeichert.", 2000);
      this.getAllVerstoesse();
    });
  }

  getSelected(verstoss: VerstossDto) : VerstossStatus {
    // Abrufen des ausgewählten Kennzeichens für die Buchung
    return this.verstoesse.find(itemVerstoss => itemVerstoss.status === verstoss.status).status;
  }
}
