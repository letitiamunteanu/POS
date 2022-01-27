import { extendObservable } from 'mobx';

/*
 *UserStore clasa
 */

 class UserStore {

    constructor(){
        extendObservable(this, {

            loading: true,
            isLoggedIn: false,
            username: ""
        })
    }
 }

 export default new UserStore();