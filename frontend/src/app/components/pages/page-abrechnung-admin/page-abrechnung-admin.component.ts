import { formatDate } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { BuchungUebersichtDto } from 'src/app/facade/dto/BuchungUebersichtDto';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';
import { DateUtils } from 'src/app/utils/date.utils';

@Component({
  selector: 'app-page-abrechnung-admin',
  templateUrl: './page-abrechnung-admin.component.html',
  styleUrls: ['./page-abrechnung-admin.component.scss']
})
export class PageAbrechnungAdminComponent implements OnInit {

  public dateStart = this.getFirstDayOfLastMonth();
  public dateEnd = this.getLastDayOfLastMonth();
  public dateMax = this.getLastDayOfLastMonth();

  private buchungen: BuchungUebersichtDto[];
  public filteredBuchungen: BuchungUebersichtDto[];

  constructor(private buchungUebersichtService: BuchungsuebersichtService) { }

  ngOnInit(): void {
    this.buchungUebersichtService.getAllBuchungen().subscribe((data: BuchungUebersichtDto[]) => {
      this.buchungen = data;
    });
  }

  public onDateStartChanged(date){
    this.dateStart = date;
    console.log("START:", date);
    this.filterBuchungen();
  }

  public onDateEndChanged(date){
    this.dateEnd = date;
    console.log("END:", date);
    this.filterBuchungen();
  }

  private filterBuchungen(){
    this.filteredBuchungen = [];

    let _dateStart = new Date(this.dateStart);
    let _dateEnd = new Date(this.dateEnd);

    for(let buchung of this.buchungen){
      if(buchung.datum >= _dateStart && buchung.datum <= _dateEnd){
        this.filteredBuchungen.push(buchung);
      }
    }
  }

  private getFirstDayOfLastMonth(){
    let date = DateUtils.getToday();

    date.setDate(1);
    date.setHours(-1);
    date.setDate(1);
    return DateUtils.toTechnicalString(date);
  }

  private getLastDayOfLastMonth(){
    let date = DateUtils.getToday();

    date.setDate(1);
    date.setHours(-1);

    return DateUtils.toTechnicalString(date);
  }

  public exportAsCsv(){
    let csvString = "Datum,Mitarbeiter,Tagespreis\n";

    for(let buchung of this.filteredBuchungen){
      csvString += formatDate(buchung.datum, 'dd.MM.YYYY', "de-DE") + "," + buchung.mitarbeiterName + "," + buchung.tagespreis + "\n";
    }



    //Download content as file
    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(csvString));
    
    let fileName = "Abrechnung_" + formatDate(new Date(this.dateStart), 'dd.MM.YYYY', "de-DE") + "-" + formatDate(new Date(this.dateEnd), 'dd.MM.YYYY', "de-DE") + ".csv";
    element.setAttribute('download', fileName);

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);

  }

}
