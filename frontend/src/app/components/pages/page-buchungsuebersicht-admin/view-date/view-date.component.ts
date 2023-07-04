import { Component, Input, OnInit } from '@angular/core';
import { BuchungUebersichtDto } from 'src/app/facade/dto/BuchungUebersichtDto';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';

@Component({
  selector: 'app-buchungsuebersicht-admin-view-date',
  templateUrl: './view-date.component.html',
  styleUrls: ['./view-date.component.scss']
})
export class BuchungsuebersichtAdminViewDateComponent implements OnInit {

  public buchungen: { key: Date, value : BuchungUebersichtDto[]}[];

  constructor(private buchungenService: BuchungsuebersichtService) { }

  ngOnInit(): void {
    this.buchungenService.getAllBuchungenMappedByDatum().subscribe(data => {
      this.buchungen = data;
    });
  }

}
