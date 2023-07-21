import { Component, OnInit } from '@angular/core';
import { AccountService } from 'src/app/services/account.service';
import { VerstossService } from 'src/app/services/verstoss.service';
import { DateUtils } from 'src/app/utils/date.utils';
import { LuxSnackbarService } from '@ihk-gfi/lux-components';
import { VerstossStatusDto } from 'src/app/facade/dto/VerstossStatus.dto';
import { VerstossDto } from 'src/app/facade/dto/Verstoss.dto';

@Component({
  selector: 'app-page-verstoss',
  templateUrl: './page-verstoss.component.html',
  styleUrls: ['./page-verstoss.component.scss']
})
export class PageVerstossComponent implements OnInit {

  public selectedDatum: Date | undefined = DateUtils.getToday();
  public minDate: string = DateUtils.getTodayAsString();
  public maxDate: string = DateUtils.getFuture_2WeeksAsString();
  public bemerkung: string = '';
  public mitarbeiterVerstoesse: VerstossDto[];
  public verstoesse: VerstossDto[];
  public isAdmin: boolean = this.accountService.isAdmin;
  public statusAuswahl: VerstossStatusDto[] = [];



  constructor(private accountService: AccountService, private verstossService: VerstossService, private snackbarService: LuxSnackbarService) { }

  ngOnInit(): void {
    this.verstossService.getAllVerstossStatus().subscribe(data => {
      this.statusAuswahl = data;

      if(!this.accountService.isAdmin) {
        this.getVertoesseForMitarbeiter(this.accountService.getMitarbeiterID());
      } else {
        this.getAllVerstoesse();
      }
    });
  }

  public speichernVerstoss(): void {
    let verstoss: VerstossDto = {
      bemerkung: this.bemerkung,
      status: undefined,
      datum: new Date(this.selectedDatum),
      meldeID: undefined
    };
    this.verstossService.speichernVerstoss(this.accountService.getMitarbeiterID(), verstoss).subscribe(() => {
      this.selectedDatum = DateUtils.getToday();
      this.bemerkung = '';
      this.getVertoesseForMitarbeiter(this.accountService.getMitarbeiterID());
    }); 
  }

  private getVertoesseForMitarbeiter(mitarbeiterID: number): void {
    this.verstossService.getVertoesseForMitarbeiter(mitarbeiterID).subscribe(data => {
      this.mitarbeiterVerstoesse = data;
      this.mapVerstossStatus(data);
    });
  }

  private getAllVerstoesse() : void {
    this.verstossService.getAllVerstoesse().subscribe(data => {
      this.verstoesse = data;
      this.mapVerstossStatus(data);
    });
  }

  private mapVerstossStatus(data: VerstossDto[]) {
    for(let verstoss of data) {
      for(let verstossStatus of this.statusAuswahl) {
        if(verstoss.status.key === verstossStatus.key) {
          verstoss.status = verstossStatus;
          break;
        }
      }
    }
  }

  //Change Methode gibt ein string zurück, jedoch wollen wir ein Date-Objekt
  public onSelectedDatumChange(date){
    this.selectedDatum = new Date(date);
  }

  changeStatusForVerstoss(status: VerstossStatusDto, verstoss: VerstossDto) {
    verstoss.status = status;
    this.verstossService.changeStatusForVerstoss(verstoss).subscribe(() => {
      this.snackbarService.openText("Statusänderung wurde gespeichert.", 2000);
      this.getAllVerstoesse();
    });
  }
}
