import { Component, OnInit } from '@angular/core';
import { AttribInfo } from '../../core/edit-list/edit-list.component';

@Component({
  selector: 'app-page-test',
  templateUrl: './page-test.component.html',
  styleUrls: ['./page-test.component.scss']
})
export class PageTestComponent implements OnInit {

  public testData = [
    {name: "Julian", age: 23, gender: "Female"},
    {name: "Otto", age: 3, gender: "Male"},
    {name: "Max", age: 99, gender: undefined}
  ];

  public testAttribs: AttribInfo[] = [
    //Validator inline
    {name: 'name', validator: (value) => {
      return value.includes(' ') ? "You cannot have spaces in the name!" : undefined;
    }},
    //Validator as a method
    {name: 'age', validator: this.ageValidator},
    {name: 'gender', typ: ["Male", "Female", "Diverse"]}
  ];

  constructor() { }

  ngOnInit(): void {
    setTimeout(() => {
      console.log("Test add item after time in parent component!");
      this.testData.push({name: "Arnold", age: 100, gender: "Male"});
    }, 2000);

    setTimeout(() => {
      console.log("++++", this.testData[0].name);
      console.log("####", this.testData[0].gender);
    }, 5000);
  }

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
