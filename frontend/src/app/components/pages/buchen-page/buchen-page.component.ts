import { Component, OnInit } from '@angular/core';
import { BuchungService } from 'src/app/services/buchung.service';
import {ParkflaecheAuswahlDto } from '../../../facade/dto/parkflaeche-auswahl.dto';
import { BuchungDto } from 'src/app/facade/dto/BuchungDto';
import { Parkflaeche } from 'src/app/facade/Parkflaeche';
import { Parkplatz } from 'src/app/facade/Parkplatz';
import { ParkplatzMitStatusDto } from 'src/app/facade/dto/ParkplatzMitStatusDto';
import { AccountService } from 'src/app/services/account.service';

@Component({
  selector: 'app-buchen-page',
  templateUrl: './buchen-page.component.html',
  styleUrls: ['./buchen-page.component.scss']
})
export class BuchenPageComponent implements OnInit {
  public parkplaetze: ParkplatzMitStatusDto[];
  protected parkanlagen : ParkflaecheAuswahlDto[] ;
  selectedParkanlage: ParkflaecheAuswahlDto = {parkflaecheID: 1};
  selectedDatum: string = new Date().toISOString();
  isAdmin: boolean = this.accountService.isAdmin;
  constructor(private buchungService: BuchungService, private accountService: AccountService) {}

  ngOnInit(): void {
    this.getParkAnlagen();


    setTimeout(() => {
      console.log("Test add item after time in parent component!");
      this.testData.push({name: "Arnold", age: 100});
    }, 2000);

    setTimeout(() => {
      console.log("++++", this.testData[0].name);
    }, 5000);
  }

  public getParkAnlagen() : ParkflaecheAuswahlDto[] {
    this.buchungService.getParkanlagen().subscribe(parkanlagen => {
      this.parkanlagen = parkanlagen;
      this.selectedParkanlage = parkanlagen[0];

    });
    return this.parkanlagen;
  }

  public buchenParkanlage() {
    console.log(this.selectedParkanlage);
  }


  public testData = [
    {name: "Julian", age: 23},
    {name: "Otto", age: 3},
    {name: "Max", age: 99}
  ];

  public testAttribs = [
    //Validator inline
    {name: 'name', validator: (value) => {
      return value.includes(' ') ? "You cannot have spaces in the name!" : undefined;
    }},
    //Validator as a method
    {name: 'age', validator: this.ageValidator}
  ]

  public onAdd(item: any){
    console.log("Added new item: ", item);
  }

  public onDelete(pos: number){
    console.log("Deleted Index: ", pos);
  }

  private ageValidator(age: string){
    if(age.match(/\%^[0-9]+\%$/)) return undefined;
    if((+age) > 0 && (+age) <= 99) return undefined;
    return "The age must be a number from 1-99!";
  }

}
