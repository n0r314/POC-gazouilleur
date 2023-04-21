import {UserDto} from "./user.dto";


export interface UserUpdateInputDto extends Partial<Omit<UserDto, 'id' | 'username' | 'password' >> {
  // un user sans id, username ni mot de passe
}
