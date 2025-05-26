<<<<<<< HEAD
CREATE TABLE prices (
  id INT AUTO_INCREMENT PRIMARY KEY,
=======
CREATE TABLE IF NOT EXISTS prices (
>>>>>>> 3f0df6d (Hotfix/v1.0.2 add backport pipeline (#22))
  brand_id INT NOT NULL,
  start_date TIMESTAMP NOT NULL,
  end_date TIMESTAMP NOT NULL,
  price_list INT NOT NULL,
  product_id INT NOT NULL,
  priority INT NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  curr VARCHAR(3) NOT NULL
);
