# spring-ecommerce-product

## feature list

- [x] configure API that services the following requests:
  - [x] add
  - [x] retrieve
  - [x] update
  - [x] delete

- [x] UI representation of products
  - [x] retrieve
  - [x] add
  - [x] update
  - [x] delete

- [x] database integration (instead of global properties)

- [ ] validation Rules
  - product name:
    - [ ] must be no more than 15 characters, including spaces
    - [ ] Allowed special characters: `( )`, `[ ]`, `+`, `-`, `&`, `/`, `_` (all other special characters are not allowed)
    - [ ] the name must be unique across all products
  - product price:
    - [ ] must be greater than 0
  - product image URL:
    - [ ] must be start with `http://` or `https://`

