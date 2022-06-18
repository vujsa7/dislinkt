import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-status-post',
  templateUrl: './status-post.component.html',
  styleUrls: ['./status-post.component.scss']
})
export class StatusPostComponent implements OnInit {

  @Input() post: any;

  constructor() { }

  ngOnInit(): void {
  }

}
