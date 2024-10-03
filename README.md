# Store  

### 구현 범위 

1. 브랜드, 상품을 추가, 수정, 삭제
2. 카테고리별 최저가격 브랜드와 상품 가격, 총액을 조회
3. 단일 브랜드로 모든 카테고리 상품 리스트 및 총액 조회
4. 카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격 조회
5. 통합테스트
6. 단위테스트 

### 설명

주어진 상품데이터를 Product라는 테이블에 저장합니다.

상품을 추가, 수정, 삭제를 통해 카테고리별 최저, 최고가격을 테이블로 관리, 업데이트 합니다.
상품을 추가, 수정, 삭제를 통해 브랜드별 모든 카테고리의 최저 총액을 테이블로 관리, 업데이트 합니다.

이렇게 유저에게 제공되는 조회성 데이터 (카테고리별 최저가격, 단일 브랜드의 모든 카테고리 최저가격) 은
인덱싱이 되어있다 하더라도 데이터베이스에 부하를 준다고 판단하여 테이블로 관리하기로 결정하였습니다.

또한, 운영상 운영자가 데이터를 추가, 수정, 삭제를 하고 이때, (카테고리별 최저가격, 단일 브랜드의 모든 카테고리 최저가격)이 결정
되기 때문에 캐싱을 하거나 테이블로 따로 저장하는게 낫다고 판단하였습니다.

그러다보면, 운영자가 데이터를 추가할때, 겹쳐서 최저가격이 제대로 업데이트되지 않을 경우가 존재하는데, 이 경우는
낙관적 락을 통해 업데이트 중에 다른 데이터가 들어오면 오류 메시지를 반환하여 재시도를 하도록 유도하였습니다.

나중에는 Queuing을 통해 들어온 순서대로 업데이트하는게 좋아보입니다.

---
카테고리별 최저가격을 저장하는 테이블에는 다음과 같은 데이터가 있습니다.
- 식별자
- 카테고리
- 최저가격 상품
- 최고가격 상품
---
단일 브랜드의 모든 카테고리 최저가격을 저장하는 테이블에는 다음과 같은 데이터가 있습니다.
- 식별자
- 브랜드
- 총합 

이 테이블은 매핑테이블을 갖고 있는데 매핑 테이블의 데이터는 다음과 같습니다.
- 식별자
- 단일 브랜드의 모든 카테고리 최저가격 테이블의 식별자 (FK)
- 상품 식별자(FK)
---
상품 추가<br>
<br>
상품을 추가하면, 
- 카테고리별 최저가격을 저장하는 테이블의 추가하는 상품의 카테고리와 가격을 비교하여
추가하는 상품의 가격이 최저가격이면 업데이트를 합니다.
- 단일 브랜드의 모든 카테고리 최저가격을 저장하는 테이블에서 추가하려하는 상품의 브랜드가 포함되어있으면,
추가하려는 상품의 카테고리를 찾아 가격을 비교하여 최저가격이면 업데이트합니다.

상품 수정<br>
1. 상품의 브랜드를 업데이트하면,
    - 카테고리별 최저가격을 저장하는 테이블에서는 브랜드만 변화하기 떄문에 업데이트를 하지 않습니다.
    - 단일 브랜드의 모든 카테고리 최저가격을 저장하는 테이블에 최저가격에 포함되는 상품이면,
가격을 업데이트하면 최저가격이 변화되어야하므로, 상품의 브랜드를 업데이트하고 해당 카테고리의 최저가격을
다시 찾아 업데이트를 합니다.
2. 상품의 가격을 업데이트
   - 먼저 상품 가격을 업데이트 함
   - 카테고리별 최저가격을 저장하는 테이블에서 업데이트하는 상품이 최저가격에 속하는 상품이라면
   해당 카테고리를 최저가격에서 제거 합니다.
   - 제거 후에 해당 카테고리의 최저가격을 업데이트합니다.
   - 단일 브랜드의 모든 카테고리 최저가격도 위와 같은 로직을 수행합니다. 
3. 상품의 카테고리를 업데이트
   - 먼저 상품의 카테고리를 업데이트합니다.
   - 카테고리별 최저가격을 저장하는 테이블에서 업데이트하는 상품이 최저가격에 속하는 상품이면
   해당 카테고리를 최저가격에서 제거 합니다.
   - 제거 후에 해당 카테고리의 최저가격을 업데이트합니다.
   - 단일 브랜드의 모든 카테고리 최저가격도 위와 같은 로직을 수행합니다.

상품 삭제<br>
상품이 삭제되면,
단일 브랜드의 모든 카테고리 최저가격과 카테고리별 최저가격을 업데이트시킵니다. 
- 카테고리별 최저가격에 속하는 상품이라면 해당 카테고리를 제거합니다.
- 단일 브랜드의 모든 카테고리 최저가격에서도 제거합니다.
- 상품을 삭제합니다.
- 카테고리별 최저가격에서 해당 카테고리를 업데이트합니다.
- 단일 브랜드의 모든 카테고리 최저가격에서 제거되었으면 업데이트합니다.

브랜드 삭제<br>
- 카테고리별 최저가격에 속하는 브랜드가 있다면 제거합니다.
- 단일 브랜드의 모든 카테고리 최저가격에서 해당 브랜드를 제거합니다.
- 해당 브랜드의 상품을 제거합니다.
- 카테고리별 최저가격에서 제거된 카테고리들을 업데이트시켜줍니다.
- 단일 브랜드의 모든 카테고리 최저가격은 업데이트할 상품이 없기 떄문에 업데이트 시키지 않습니다. 

---
### API END-POINT
swagger를 사용하여 쉽게 볼 수 있도록 하였습니다.
<br>
서버 실행 후 아래 링크를 통해 접속 
<br>
http://localhost:8080/swagger-ui/index.html#/


### 실행 방법

Test run
```bash
./gradlew clean test
```

Server run
```bash
docker-compose up
```

