import { Component, OnInit } from '@angular/core';
import { BuchungUebersichtDto } from 'src/app/facade/dto/BuchungUebersichtDto';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';

@Component({
  selector: 'app-buchungsuebersicht-admin-view-mitarbeiter',
  templateUrl: './view-mitarbeiter.component.html',
  styleUrls: ['./view-mitarbeiter.component.scss']
})
export class BuchungsuebersichtAdminViewMitarbeiterComponent implements OnInit {

  public buchungen: { key: string, value : BuchungUebersichtDto[]}[];

  constructor(private buchungenService: BuchungsuebersichtService) { }

  ngOnInit(): void {
    this.buchungenService.getAllBuchungenMappedByMitarbeiter().subscribe(data => {
      this.buchungen = data;
    });
  }

}
