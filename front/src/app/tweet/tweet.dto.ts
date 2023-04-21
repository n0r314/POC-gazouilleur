import {AuthorDto} from "../user/author.dto";


export interface TweetDto {
  id: string;
  content: string;
  createdAt: Date;
  author: AuthorDto;
}
