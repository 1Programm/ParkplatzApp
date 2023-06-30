import { Component, OnInit } from '@angular/core';
import { ParkhausParkflaecheDto } from 'src/app/facade/dto/ParkhausParkflaecheDto';
import { AdminService } from 'src/app/services/admin.service';

@Component({
  selector: 'app-admin-edit',
  templateUrl: './admin-edit.component.html',
  styleUrls: ['./admin-edit.component.scss']
})
export class AdminEditComponent implements OnInit {

  constructor(private adminService: AdminService) { }

  public parkhaeuser: ParkhausParkflaecheDto[]; 
  public attr = [
    {name: 'bezeichnung'}
  ];
  ngOnInit(): void {
    this.adminService.getAllParkhausAndParkflaeche().subscribe(parkhaeuser => {this.parkhaeuser = parkhaeuser});
    
  }

  public saveParkflaeche(parkflaeche) {
    console.log("bla", parkflaeche);
  }

  public deleteParkflaeche(parkflaeche) {
  
  }

}
