import { Component, OnInit } from '@angular/core';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';

@Component({
  selector: 'app-page-buchungsuebersicht-admin',
  templateUrl: './page-buchungsuebersicht-admin.component.html',
  styleUrls: ['./page-buchungsuebersicht-admin.component.scss']
})
export class PageBuchungsuebersichtAdminComponent implements OnInit {

  public stateOptions: string[] = [
    'Alle', 'Datum', 'Mitarbeiter'
  ];

  public viewState: string = this.stateOptions[0];

  constructor(private buchungenService: BuchungsuebersichtService) { }

  ngOnInit(): void {
  }

}
