export class Product {
  id: number = 0;
  print_id: string = '';
  code: string = '';
  name: string = '';
  description: string = '';
  subcategory: string = '';
  price: number = 0.0;
  stock: number = 0;
  like_cnt: number = 0;
  dislike_cnt: number = 0;
  image: string = '';
  constructor(
    id: number,
    print_id: string,
    code: string,
    name: string,
    description: string,
    subcategory: string,
    price: number,
    stock: number,
    like_cnt: number,
    dislike_cnt: number,
    image: string
  ) {
    this.id = id;
    this.print_id = print_id;
    this.code = code;
    this.name = name;
    this.description = description;
    this.subcategory = subcategory;
    this.price = price;
    this.stock = stock;
    this.like_cnt = like_cnt;
    this.dislike_cnt = dislike_cnt;
    this.image = image;
  }
  
}