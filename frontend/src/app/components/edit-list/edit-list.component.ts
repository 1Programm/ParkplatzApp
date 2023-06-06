import { Component, Input } from '@angular/core';

@Component({
  selector: 'edit-list',
  templateUrl: './edit-list.component.html',
  styleUrls: ['./edit-list.component.scss']
})

export class EditListComponent {

  @Input() items: Array<any> | undefined;
  @Input()
  attributes!: Array<any>;
  @Input() canDelete: Function | undefined;
  @Input() canEdit = true;
  @Input() onDelete: Function | undefined;

  constructor() { }

  getAttributes(item: any) {
    let attrs = [];

    if(this.attributes){
      for(let i=0;i<this.attributes.length;i++){
        let attribute = this.attributes[i];

        let value = item[attribute.name];

        attrs.push({control: attribute.control, name: attribute.label, value});
      }
    } else {
      for(let name in item){
        let value = item[name];
        attrs.push({name, value});
      }
    }

    return attrs;
  }

  checkCanDelete(item: any){
    return (!this.canDelete) || this.canDelete(item);
    /*if(this.canDelete){
      return this.canDelete();
    }
    else {
      return true;
    }*/
  }

  loescheItem(index: any) {
    if(this.onDelete) {
      this.onDelete(index);
    }
  }

}

