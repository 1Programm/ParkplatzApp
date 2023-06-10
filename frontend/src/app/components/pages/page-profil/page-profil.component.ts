import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ILuxDialogConfig, LuxDialogService } from '@ihk-gfi/lux-components';
import { Kennzeichen } from 'src/app/facade/Kennezeichen';
import { Mitarbeiter } from 'src/app/facade/Mitarbeiter';
import { ProfilServiceService } from 'src/app/services/profil-service.service';

@Component({
  selector: 'app-page-profil',
  templateUrl: './page-profil.component.html',
  styleUrls: ['./page-profil.component.scss']
})
export class PageProfilComponent implements OnInit {

public attr = [
  {name: 'kennzeichen', validator: this.kennzeichenValidator}
]
mitarbeiter: Mitarbeiter = {
  id: 0,
  vorname: '',
  nachname: '',
  mail: '',
  kennzeichenList: []
}
mitarbeiterID: number;


public kennzeichenDeleteCallback: Function | undefined;
  constructor(private activatedRoute: ActivatedRoute, private profilService: ProfilServiceService) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.mitarbeiterID = params['mitarbeiterID'];
      this.profilService.getMitarbeiter(this.mitarbeiterID).subscribe((data: Mitarbeiter) => {
      this.mitarbeiter = data;   
      });
    }); 
  }

  public deleteKennzeichen(toDelete: any): void {
      this.profilService.deleteKennzeichenFromMitarbeiter({ mitarbeiterID: this.mitarbeiterID, kennzeichenID: toDelete.kennzeichenID }).subscribe(updated => {
        this.mitarbeiter = updated;
      });
    }
    
  public saveKennzeichen(toSave: any) {
    this.profilService.createKennzeichenForMitarbeiter(this.mitarbeiterID, toSave.kennzeichen).subscribe(updated => {
      this.mitarbeiter = updated;
    });
  }

  public getName(): string {
    return this.mitarbeiter.vorname + " " + this.mitarbeiter.nachname;
  }

  private kennzeichenValidator(kennzeichen: string){
    if(kennzeichen.match('^[A-ZÄÖÜ]{1,3}\-[ ]{0,1}[A-Z]{0,2}[0-9]{1,4}[H]{0,1}')) return undefined;
    return "Das angegebene Kennzeichen entspricht nicht der Form XXX-YY1111";
  }
}
