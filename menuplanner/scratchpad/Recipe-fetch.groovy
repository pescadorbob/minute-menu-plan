import com.mp.domain.*;

def lids = '(6532)'
def    filteredResults = Recipe.findAll('from Recipe as r left join fetch r.image \
    inner join fetch r.ingredients ingredients join fetch ingredients.ingredient \
    where r.id in ' + lids).unique()
