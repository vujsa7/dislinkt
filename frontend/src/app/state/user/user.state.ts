import { Injectable } from "@angular/core";
import { Action, createSelector, Selector, State, StateContext } from "@ngxs/store";
import { passedXMinutes } from "src/app/core/utils/timer.util";
import { UserService } from "src/app/shared/services/user.service";
import { UserFullnameAndImage } from "./user-fullname-and-image.model";
import { GetUserFullnameAndImage } from "./user.actions";
import { patch, insertItem, iif, compose, removeItem } from '@ngxs/store/operators';
import { tap } from 'rxjs/operators';
import * as _ from "lodash";


export class UserStateModel {
    userFullnamesAndImages!: UserFullnameAndImage[];
}

@State<UserStateModel>({
    name: 'userstate',
    defaults: {
        userFullnamesAndImages: []
    }
})
@Injectable()
export class UserState {

    constructor(private userService: UserService) { }

    @Selector()
    static selectUserFullnameAndImage(state: UserStateModel) {
        return (id: string) => {
            return state.userFullnamesAndImages.find(u => u.id == id);
        };
    }

    @Selector()
    static selectUserFullnamesAndImages(state: UserStateModel) {
        return state.userFullnamesAndImages;
    }

    @Action(GetUserFullnameAndImage)
    getUserFullnameAndImageFromState(ctx: StateContext<UserStateModel>, { id }: GetUserFullnameAndImage) {
        let state = ctx.getState();
        let user = _.find(state.userFullnamesAndImages, user => user.id == id) 
        if (user != null && !passedXMinutes(new Date(user.dateSaved), 20))
            return;
        return this.userService.fetchNameAndImage(id).pipe(tap(
            data => {
                let state = ctx.getState();
                data.dateSaved = new Date();
                let userIndex = _.findIndex(state.userFullnamesAndImages, u => u.id == data.id);
                if(userIndex != -1)
                    ctx.setState(
                        patch({
                            userFullnamesAndImages: removeItem<UserFullnameAndImage>(userIndex)
                        })
                    )
                ctx.setState(
                    patch({
                        userFullnamesAndImages: insertItem<UserFullnameAndImage>(data)
                    })
                )
            }
        ))
    }

}