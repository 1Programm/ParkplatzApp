import { Component, OnInit } from '@angular/core';
import { VerstossDto } from 'src/app/facade/dto/verstoss.dto';
import { AccountService } from 'src/app/services/account.service';
import { VerstossService } from 'src/app/services/verstoss.service';
import { VerstossStatus } from 'src/app/utils/verstossStatus.enum';
import { DateUtils } from 'src/app/utils/date.utils';

@Component({
  selector: 'app-page-verstoss',
  templateUrl: './page-verstoss.component.html',
  styleUrls: ['./page-verstoss.component.scss']
})
export class PageVerstossComponent implements OnInit {

  public selectedDatum: string = DateUtils.toVisibleString(new Date());
  public bemerkung: string = '';
  public verstoesse: VerstossDto[];

  private verstoss: VerstossDto = {
    mitarbeiterID: 1,
    verstossID: 1,
    datum: new Date(),
    bemerkung: '',
    status: VerstossStatus.IN_BEARBEITUNG
  };

  constructor(private accountService: AccountService, private verstossService: VerstossService) { }

  ngOnInit(): void {
    this.verstoss.mitarbeiterID = this.accountService.getMitarbeiterID();
    this.getVertoesse(this.verstoss.mitarbeiterID);
  }

  public speichernVerstoss(): void {
    this.verstoss.bemerkung = this.bemerkung;
    this.verstoss.datum = new Date(this.selectedDatum);
    this.verstossService.speichernVerstoss(this.verstoss).subscribe(() => {
      this.selectedDatum = '';
      this.bemerkung = '';
      this.getVertoesse(this.verstoss.mitarbeiterID);
    });
    
  }

  private getVertoesse(mitarbeiterID: number): void {
    this.verstossService.getVerstoesse(mitarbeiterID).subscribe(data => {
      this.verstoesse = data;
    });
  }

  public changeDateFormat(datum: Date): string {
    return DateUtils.toVisibleString(datum);
  }

  public getZugelasenesDatum(): string {
    return DateUtils.toTechnicalString(new Date());
  }
}
