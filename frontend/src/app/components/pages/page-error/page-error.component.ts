import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
    selector: 'app-error',
    templateUrl: './page-error.component.html',
    styleUrls: ['./page-error.component.scss']
})
export class PageErrorComponent implements OnInit {
    public url404 = '';

    constructor(private readonly router: Router) {}

    ngOnInit(): void {
        this.url404 = this.router.routerState.snapshot.url;
    }

}
