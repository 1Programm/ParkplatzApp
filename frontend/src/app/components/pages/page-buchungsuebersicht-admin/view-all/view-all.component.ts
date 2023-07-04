import { Component, Input, OnInit } from '@angular/core';
import { BuchungUebersichtDto } from 'src/app/facade/dto/BuchungUebersichtDto';
import { BuchungsuebersichtService } from 'src/app/services/buchungsuebersicht.service';

@Component({
  selector: 'app-buchungsuebersicht-admin-view-all',
  templateUrl: './view-all.component.html',
  styleUrls: ['./view-all.component.scss']
})
export class BuchungsuebersichtAdminViewAllComponent implements OnInit {
  
  public buchungen: BuchungUebersichtDto[];

  constructor(private buchungenService: BuchungsuebersichtService) { }

  ngOnInit(): void {
    this.buchungenService.getAllBuchungen().subscribe((data: BuchungUebersichtDto[]) => {
      this.buchungen = data;
    });
  }

}
