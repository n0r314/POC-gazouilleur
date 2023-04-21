import {AuthorDto} from "../user/author.dto";


export interface CommentDto {
  id: string;
  author: AuthorDto;
  content: string;
  createdAt: Date;
}
