export class UserFullnameAndImage {

    id: string;
    fullName: string;
    image: string;
    dateSaved: Date;

    constructor(
        id: string,
        fullName: string,
        image: string,
        dateSaved: Date
    ) {
        this.id = id
        this.fullName = fullName
        this.image = image
        this.dateSaved = dateSaved
    }



}