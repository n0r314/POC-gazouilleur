import {UserDto} from "../user/user.dto";

export type AuthOutputDto = {
 user: UserDto;
 token: string;
};
