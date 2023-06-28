import { Component, OnInit } from '@angular/core';
import { Mitarbeiter } from 'src/app/facade/Mitarbeiter';
import { VerstossDto } from 'src/app/facade/dto/verstoss.dto';
import { AccountService } from 'src/app/services/account.service';
import { ProfilServiceService } from 'src/app/services/profil-service.service';
import { VerstossService } from 'src/app/services/verstoss.service';
import { VerstossStatus } from 'src/app/utils/verstossStatus.enum';

@Component({
  selector: 'app-page-verstoss',
  templateUrl: './page-verstoss.component.html',
  styleUrls: ['./page-verstoss.component.scss']
})
export class PageVerstossComponent implements OnInit {

  selectedDatum: string = '';
  beschreibung: string = '';
  verstoesse: VerstossDto[];
  verstoss: VerstossDto = {
    mitarbeiterID: 1,
    verstossID: 1,
    datum: new Date(),
    bemerkung: '',
    status: VerstossStatus.IN_BEARBEITUNG
  };

  constructor(private accountService: AccountService, private profilService: ProfilServiceService, private verstossService: VerstossService) { }

  ngOnInit(): void {
    this.accountService.getMitarbeiterIDAsObservable().subscribe(mitarbeiterID => {
      this.profilService.getMitarbeiter(mitarbeiterID).subscribe((data: Mitarbeiter) => {
        this.verstoss.mitarbeiterID = mitarbeiterID; 
          console.log(data.verstossList);
      });
    });
  }

  speichernVerstoss() {
    this.verstoss.bemerkung = this.beschreibung;
    this.verstoss.datum = new Date(this.selectedDatum);
    console.log("Verstoss: ", this.verstoss);
    this.verstossService.saveKennzeichenForBuchung(this.verstoss).subscribe();
  }
}
